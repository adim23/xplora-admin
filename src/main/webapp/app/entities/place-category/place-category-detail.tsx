import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './place-category.reducer';

export const PlaceCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const placeCategoryEntity = useAppSelector(state => state.placeCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="placeCategoryDetailsHeading">
          <Translate contentKey="xploraAdminApp.placeCategory.detail.title">PlaceCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{placeCategoryEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.placeCategory.code">Code</Translate>
            </span>
          </dt>
          <dd>{placeCategoryEntity.code}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.placeCategory.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{placeCategoryEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="xploraAdminApp.placeCategory.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{placeCategoryEntity.icon}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.placeCategory.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {placeCategoryEntity.createdDate ? (
              <TextFormat value={placeCategoryEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.placeCategory.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{placeCategoryEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.placeCategory.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {placeCategoryEntity.defaultImageData ? (
              <div>
                {placeCategoryEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(placeCategoryEntity.defaultImageDataContentType, placeCategoryEntity.defaultImageData)}>
                    <img
                      src={`data:${placeCategoryEntity.defaultImageDataContentType};base64,${placeCategoryEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {placeCategoryEntity.defaultImageDataContentType}, {byteSize(placeCategoryEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.placeCategory.createdBy">Created By</Translate>
          </dt>
          <dd>{placeCategoryEntity.createdBy ? placeCategoryEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.placeCategory.place">Place</Translate>
          </dt>
          <dd>
            {placeCategoryEntity.places
              ? placeCategoryEntity.places.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {placeCategoryEntity.places && i === placeCategoryEntity.places.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/place-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/place-category/${placeCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaceCategoryDetail;
