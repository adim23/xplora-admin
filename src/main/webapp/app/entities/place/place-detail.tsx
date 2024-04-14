import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './place.reducer';

export const PlaceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const placeEntity = useAppSelector(state => state.place.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="placeDetailsHeading">
          <Translate contentKey="xploraAdminApp.place.detail.title">Place</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{placeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.place.code">Code</Translate>
            </span>
          </dt>
          <dd>{placeEntity.code}</dd>
          <dt>
            <span id="destinationSight">
              <Translate contentKey="xploraAdminApp.place.destinationSight">Destination Sight</Translate>
            </span>
          </dt>
          <dd>{placeEntity.destinationSight ? 'true' : 'false'}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="xploraAdminApp.place.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{placeEntity.longitude}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="xploraAdminApp.place.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{placeEntity.latitude}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.place.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {placeEntity.createdDate ? <TextFormat value={placeEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.place.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{placeEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.place.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {placeEntity.defaultImageData ? (
              <div>
                {placeEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(placeEntity.defaultImageDataContentType, placeEntity.defaultImageData)}>
                    <img
                      src={`data:${placeEntity.defaultImageDataContentType};base64,${placeEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {placeEntity.defaultImageDataContentType}, {byteSize(placeEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.place.createdBy">Created By</Translate>
          </dt>
          <dd>{placeEntity.createdBy ? placeEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.place.tags">Tags</Translate>
          </dt>
          <dd>
            {placeEntity.tags
              ? placeEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {placeEntity.tags && i === placeEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.place.category">Category</Translate>
          </dt>
          <dd>
            {placeEntity.categories
              ? placeEntity.categories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {placeEntity.categories && i === placeEntity.categories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.place.destination">Destination</Translate>
          </dt>
          <dd>{placeEntity.destination ? placeEntity.destination.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/place" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/place/${placeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaceDetail;
