import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vehicle.reducer';

export const VehicleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vehicleEntity = useAppSelector(state => state.vehicle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vehicleDetailsHeading">
          <Translate contentKey="xploraAdminApp.vehicle.detail.title">Vehicle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.id}</dd>
          <dt>
            <span id="plate">
              <Translate contentKey="xploraAdminApp.vehicle.plate">Plate</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.plate}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.vehicle.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="xploraAdminApp.vehicle.type">Type</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.type}</dd>
          <dt>
            <span id="capacity">
              <Translate contentKey="xploraAdminApp.vehicle.capacity">Capacity</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.capacity}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="xploraAdminApp.vehicle.color">Color</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.color}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.vehicle.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {vehicleEntity.createdDate ? <TextFormat value={vehicleEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.vehicle.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.vehicle.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {vehicleEntity.defaultImageData ? (
              <div>
                {vehicleEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(vehicleEntity.defaultImageDataContentType, vehicleEntity.defaultImageData)}>
                    <img
                      src={`data:${vehicleEntity.defaultImageDataContentType};base64,${vehicleEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {vehicleEntity.defaultImageDataContentType}, {byteSize(vehicleEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/vehicle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vehicle/${vehicleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VehicleDetail;
