import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './destination.reducer';

export const DestinationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const destinationEntity = useAppSelector(state => state.destination.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="destinationDetailsHeading">
          <Translate contentKey="xploraAdminApp.destination.detail.title">Destination</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{destinationEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.destination.code">Code</Translate>
            </span>
          </dt>
          <dd>{destinationEntity.code}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.destination.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{destinationEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.destination.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{destinationEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.destination.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {destinationEntity.defaultImageData ? (
              <div>
                {destinationEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(destinationEntity.defaultImageDataContentType, destinationEntity.defaultImageData)}>
                    <img
                      src={`data:${destinationEntity.defaultImageDataContentType};base64,${destinationEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {destinationEntity.defaultImageDataContentType}, {byteSize(destinationEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="cssStyle">
              <Translate contentKey="xploraAdminApp.destination.cssStyle">Css Style</Translate>
            </span>
          </dt>
          <dd>{destinationEntity.cssStyle}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.destination.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {destinationEntity.createdDate ? (
              <TextFormat value={destinationEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.destination.createdBy">Created By</Translate>
          </dt>
          <dd>{destinationEntity.createdBy ? destinationEntity.createdBy.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/destination" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/destination/${destinationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DestinationDetail;
